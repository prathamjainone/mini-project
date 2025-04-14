import React, { useState, useEffect } from 'react';
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  TextField,
  Button,
  Paper,
  Checkbox,
  CircularProgress,
  Alert,
  Snackbar,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import taskService from '../services/taskService';

function Tasks() {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  // Fetch tasks on component mount
  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      setLoading(true);
      const data = await taskService.getAllTasks();
      setTasks(data);
      setError(null);
    } catch (err) {
      console.error('Error fetching tasks:', err);
      setError('Failed to load tasks. Please try again later.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddTask = async () => {
    if (newTask.trim()) {
      try {
        // Send only the title for natural language parsing
        const taskData = {
          title: newTask
        };
        
        const createdTask = await taskService.createTask(taskData);
        setTasks([...tasks, createdTask]);
        setNewTask('');
        setSnackbar({
          open: true,
          message: 'Task added successfully',
          severity: 'success'
        });
      } catch (err) {
        console.error('Error adding task:', err);
        setSnackbar({
          open: true,
          message: 'Failed to add task. Please try again.',
          severity: 'error'
        });
      }
    }
  };

  const handleToggleTask = async (taskId) => {
    try {
      const updatedTask = await taskService.toggleTaskCompletion(taskId);
      setTasks(
        tasks.map((task) =>
          task.id === taskId ? updatedTask : task
        )
      );
      setSnackbar({
        open: true,
        message: 'Task updated successfully',
        severity: 'success'
      });
    } catch (err) {
      console.error(`Error toggling task ${taskId}:`, err);
      setSnackbar({
        open: true,
        message: 'Failed to update task. Please try again.',
        severity: 'error'
      });
    }
  };

  const handleDeleteTask = async (taskId) => {
    try {
      await taskService.deleteTask(taskId);
      setTasks(tasks.filter((task) => task.id !== taskId));
      setSnackbar({
        open: true,
        message: 'Task deleted successfully',
        severity: 'success'
      });
    } catch (err) {
      console.error(`Error deleting task ${taskId}:`, err);
      setSnackbar({
        open: true,
        message: 'Failed to delete task. Please try again.',
        severity: 'error'
      });
    }
  };

  const handleCloseSnackbar = () => {
    setSnackbar({ ...snackbar, open: false });
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Tasks
      </Typography>
      
      {error && (
        <Alert severity="error" sx={{ mb: 2 }}>
          {error}
        </Alert>
      )}
      
      <Paper sx={{ p: 2, mb: 3 }}>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <Box sx={{ display: 'flex', gap: 2 }}>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Try: 'Buy groceries tomorrow at 3pm high priority'"
              value={newTask}
              onChange={(e) => setNewTask(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleAddTask()}
            />
            <Button
              variant="contained"
              startIcon={<AddIcon />}
              onClick={handleAddTask}
            >
              Add
            </Button>
          </Box>
          <Typography variant="caption" color="textSecondary">
            Try using natural language! Examples:
            <ul>
              <li>"Call John tomorrow at 3pm" - Sets due date and time</li>
              <li>"Buy groceries high priority" - Sets priority to HIGH</li>
              <li>"Submit report next week" - Sets due date to next week</li>
            </ul>
          </Typography>
        </Box>
      </Paper>
      
      {tasks.length === 0 ? (
        <Typography variant="body1" color="textSecondary" align="center">
          No tasks found. Add a new task to get started.
        </Typography>
      ) : (
        <List>
          {tasks.map((task) => (
            <ListItem
              key={task.id}
              sx={{
                bgcolor: 'background.paper',
                mb: 1,
                borderRadius: 1,
              }}
            >
              <Checkbox
                checked={task.completed || false}
                onChange={() => handleToggleTask(task.id)}
              />
              <ListItemText
                primary={
                  <Box component="div">
                    <Typography
                      component="span"
                      sx={{
                        textDecoration: task.completed ? 'line-through' : 'none',
                      }}
                    >
                      {task.title}
                    </Typography>
                    {task.priority === 'HIGH' && (
                      <Typography
                        component="span"
                        color="error"
                        sx={{ ml: 1 }}
                      >
                        (High Priority)
                      </Typography>
                    )}
                    {task.dueDate && (
                      <Typography
                        component="span"
                        color="textSecondary"
                        sx={{ ml: 1 }}
                      >
                        (Due: {new Date(task.dueDate).toLocaleString()})
                      </Typography>
                    )}
                  </Box>
                }
                secondary={task.description}
              />
              <ListItemSecondaryAction>
                <IconButton
                  edge="end"
                  aria-label="delete"
                  onClick={() => handleDeleteTask(task.id)}
                >
                  <DeleteIcon />
                </IconButton>
              </ListItemSecondaryAction>
            </ListItem>
          ))}
        </List>
      )}
      
      <Snackbar
        open={snackbar.open}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      >
        <Alert onClose={handleCloseSnackbar} severity={snackbar.severity}>
          {snackbar.message}
        </Alert>
      </Snackbar>
    </Box>
  );
}

export default Tasks; 