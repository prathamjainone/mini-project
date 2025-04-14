import React, { useState } from 'react';
import { useGoogleLogin } from '@react-oauth/google';
import { Button, Snackbar, Alert } from '@mui/material';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';

const GoogleCalendarIntegration = ({ tasks, onSyncComplete }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [notification, setNotification] = useState({ open: false, message: '', severity: 'info' });

  const handleSuccess = async (tokenResponse) => {
    setIsLoading(true);
    try {
      console.log('Starting calendar sync...');
      const accessToken = tokenResponse.access_token;
      console.log('Got access token');
      
      // First get or create calendar
      const calendar = await getOrCreateCalendar(accessToken);
      console.log('Got calendar:', calendar);
      
      // Sync each task
      let syncedCount = 0;
      for (const task of tasks) {
        try {
          await createOrUpdateEvent(accessToken, calendar.id, task);
          syncedCount++;
          console.log(`Synced task: ${task.title}`);
        } catch (error) {
          console.error(`Failed to sync task ${task.title}:`, error);
        }
      }

      setNotification({
        open: true,
        message: `Successfully synced ${syncedCount} tasks to Google Calendar!`,
        severity: 'success'
      });
      
      if (onSyncComplete) {
        onSyncComplete();
      }
    } catch (error) {
      console.error('Calendar sync error:', error);
      setNotification({
        open: true,
        message: `Failed to sync with Google Calendar: ${error.message}`,
        severity: 'error'
      });
    } finally {
      setIsLoading(false);
    }
  };

  const login = useGoogleLogin({
    onSuccess: handleSuccess,
    scope: 'https://www.googleapis.com/auth/calendar.events https://www.googleapis.com/auth/calendar',
    onError: (error) => {
      console.error('Google login failed:', error);
      setNotification({
        open: true,
        message: 'Google login failed. Please try again.',
        severity: 'error'
      });
      setIsLoading(false);
    },
    flow: 'implicit',
    ux_mode: 'popup'
  });

  const getOrCreateCalendar = async (accessToken) => {
    try {
      console.log('Checking for existing calendar...');
      // First try to find a calendar named "Task Master"
      const listResponse = await fetch('https://www.googleapis.com/calendar/v3/users/me/calendarList', {
        headers: {
          'Authorization': `Bearer ${accessToken}`,
        }
      });
      
      if (!listResponse.ok) {
        throw new Error('Failed to fetch calendars');
      }

      const calendars = await listResponse.json();
      const existingCalendar = calendars.items?.find(cal => cal.summary === 'Task Master');
      
      if (existingCalendar) {
        console.log('Found existing calendar');
        return existingCalendar;
      }

      console.log('Creating new calendar...');
      // If not found, create new calendar
      const createResponse = await fetch('https://www.googleapis.com/calendar/v3/calendars', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          summary: 'Task Master',
          description: 'Calendar for Task Master tasks',
          timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone
        })
      });
      
      if (!createResponse.ok) {
        throw new Error('Failed to create calendar');
      }

      return await createResponse.json();
    } catch (error) {
      console.error('Error accessing/creating calendar:', error);
      throw new Error('Failed to setup calendar');
    }
  };

  const createOrUpdateEvent = async (accessToken, calendarId, task) => {
    const event = {
      summary: task.title,
      description: `Priority: ${task.priority}\nTask from Task Master`,
      start: {
        dateTime: new Date().toISOString(),
        timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone
      },
      end: {
        dateTime: new Date(Date.now() + 3600000).toISOString(), // 1 hour duration
        timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone
      },
      colorId: task.priority === 'HIGH' ? '11' : task.priority === 'MEDIUM' ? '5' : '9',
      transparency: task.completed ? 'transparent' : 'opaque'
    };

    try {
      const response = await fetch(`https://www.googleapis.com/calendar/v3/calendars/${calendarId}/events`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(event)
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.error?.message || 'Failed to create event');
      }

      return await response.json();
    } catch (error) {
      console.error('Error creating event:', error);
      throw error;
    }
  };

  const handleCloseNotification = () => {
    setNotification({ ...notification, open: false });
  };

  return (
    <>
      <Button
        variant="contained"
        color="primary"
        startIcon={<CalendarMonthIcon />}
        onClick={() => login()}
        disabled={isLoading}
        style={{ marginLeft: '10px' }}
      >
        {isLoading ? 'Syncing...' : 'Sync with Google Calendar'}
      </Button>

      <Snackbar
        open={notification.open}
        autoHideDuration={6000}
        onClose={handleCloseNotification}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert 
          onClose={handleCloseNotification} 
          severity={notification.severity}
          elevation={6}
          variant="filled"
        >
          {notification.message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default GoogleCalendarIntegration; 