import React, { useState } from 'react';
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
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';

function Tasks() {
  const [tasks, setTasks] = useState([
    { id: 1, title: 'Complete project documentation', completed: false },
    { id: 2, title: 'Review code changes', completed: true },
    { id: 3, title: 'Schedule team meeting', completed: false },
  ]);
  const [newTask, setNewTask] = useState('');

  const handleAddTask = () => {
    if (newTask.trim()) {
      setTasks([
        ...tasks,
        {
          id: tasks.length + 1,
          title: newTask,
          completed: false,
        },
      ]);
      setNewTask('');
    }
  };

  const handleToggleTask = (taskId) => {
    setTasks(
      tasks.map((task) =>
        task.id === taskId ? { ...task, completed: !task.completed } : task
      )
    );
  };

  const handleDeleteTask = (taskId) => {
    setTasks(tasks.filter((task) => task.id !== taskId));
  };

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Tasks
      </Typography>
      <Paper sx={{ p: 2, mb: 3 }}>
        <Box sx={{ display: 'flex', gap: 2 }}>
          <TextField
            fullWidth
            variant="outlined"
            placeholder="Add a new task"
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
      </Paper>
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
              checked={task.completed}
              onChange={() => handleToggleTask(task.id)}
            />
            <ListItemText
              primary={task.title}
              sx={{
                textDecoration: task.completed ? 'line-through' : 'none',
              }}
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
    </Box>
  );
}

export default Tasks; 