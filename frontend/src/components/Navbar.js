import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  Box,
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import TaskIcon from '@mui/icons-material/Task';
import PersonIcon from '@mui/icons-material/Person';

function Navbar() {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Productivity App
        </Typography>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <Button
            color="inherit"
            component={RouterLink}
            to="/"
            startIcon={<DashboardIcon />}
          >
            Dashboard
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/tasks"
            startIcon={<TaskIcon />}
          >
            Tasks
          </Button>
          <Button
            color="inherit"
            component={RouterLink}
            to="/profile"
            startIcon={<PersonIcon />}
          >
            Profile
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}

export default Navbar; 