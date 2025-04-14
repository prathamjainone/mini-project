import React, { useState, useEffect } from 'react';
import './App.css';
import GoogleCalendarIntegration from './components/GoogleCalendarIntegration';

function App() {
  const [tasks, setTasks] = useState([]);
  const [newTask, setNewTask] = useState('');
  const [newTaskPriority, setNewTaskPriority] = useState('MEDIUM');
  const [error, setError] = useState(null);
  const [darkMode, setDarkMode] = useState(() => {
    const savedTheme = localStorage.getItem('theme');
    return savedTheme ? savedTheme === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches;
  });

  const PRIORITIES = ['LOW', 'MEDIUM', 'HIGH'];
  
  const priorityColors = {
    HIGH: darkMode ? 'danger' : 'danger',
    MEDIUM: darkMode ? 'warning' : 'warning',
    LOW: darkMode ? 'info' : 'info'
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  useEffect(() => {
    document.body.className = darkMode ? 'dark-mode' : 'light-mode';
    localStorage.setItem('theme', darkMode ? 'dark' : 'light');
  }, [darkMode]);

  const fetchTasks = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/tasks');
      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to fetch tasks');
      }
      const data = await response.json();
      setTasks(data);
      setError(null);
    } catch (error) {
      console.error('Error fetching tasks:', error);
      setError(error.message || 'Failed to load tasks. Please try again later.');
    }
  };

  const addTask = async (e) => {
    e.preventDefault();
    if (!newTask.trim()) {
      setError('Task title cannot be empty');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/api/tasks', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: newTask.trim(),
          description: '',
          priority: newTaskPriority
        }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to add task');
      }

      setNewTask('');
      setNewTaskPriority('MEDIUM');
      fetchTasks();
      setError(null);
    } catch (error) {
      console.error('Error adding task:', error);
      setError(error.message || 'Failed to add task. Please try again.');
    }
  };

  const toggleTask = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${id}/toggle`, {
        method: 'PUT',
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to toggle task');
      }

      fetchTasks();
      setError(null);
    } catch (error) {
      console.error('Error toggling task:', error);
      setError(error.message || 'Failed to toggle task. Please try again.');
    }
  };

  const deleteTask = async (id) => {
    if (!window.confirm('Are you sure you want to delete this task?')) return;

    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${id}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to delete task');
      }

      fetchTasks();
      setError(null);
    } catch (error) {
      console.error('Error deleting task:', error);
      setError(error.message || 'Failed to delete task. Please try again.');
    }
  };

  const updateTaskPriority = async (id, newPriority) => {
    try {
      const response = await fetch(`http://localhost:8080/api/tasks/${id}/priority`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ priority: newPriority }),
      });

      if (!response.ok) {
        const errorData = await response.json().catch(() => null);
        throw new Error(errorData?.message || 'Failed to update priority');
      }

      fetchTasks();
      setError(null);
    } catch (error) {
      console.error('Error updating priority:', error);
      setError(error.message || 'Failed to update priority. Please try again.');
    }
  };

  const toggleTheme = () => {
    setDarkMode(!darkMode);
  };

  return (
    <div className="App">
      <div className={`container ${darkMode ? 'dark-mode' : 'light-mode'}`}>
        <div className="d-flex justify-content-between align-items-center mb-4">
          <h1>Task Master by Pratham Jain</h1>
          <div className="d-flex align-items-center">
            <GoogleCalendarIntegration 
              tasks={tasks} 
              onSyncComplete={() => {
                setError(null);
                fetchTasks();
              }}
            />
            <button
              className={`theme-toggle btn ${darkMode ? 'btn-light' : 'btn-dark'} ms-2`}
              onClick={toggleTheme}
            >
              {darkMode ? '☀️ Light' : '🌙 Dark'}
            </button>
          </div>
        </div>
        
        {error && (
          <div className="alert alert-danger" role="alert">
            {error}
          </div>
        )}
        
        <form onSubmit={addTask} className="mb-4">
          <div className="input-group">
            <input
              type="text"
              className={`form-control ${darkMode ? 'dark-input' : ''}`}
              value={newTask}
              onChange={(e) => setNewTask(e.target.value)}
              placeholder="What needs to be done?"
              required
            />
            <select
              className={`form-select ${darkMode ? 'dark-input' : ''}`}
              style={{ maxWidth: '150px' }}
              value={newTaskPriority}
              onChange={(e) => setNewTaskPriority(e.target.value)}
            >
              {PRIORITIES.map(priority => (
                <option key={priority} value={priority}>{priority}</option>
              ))}
            </select>
            <button type="submit" className="btn btn-primary">
              Add Task
            </button>
          </div>
        </form>

        {tasks.length === 0 ? (
          <div className={`alert ${darkMode ? 'alert-dark' : 'alert-info'} text-center`}>
            <p className="mb-0">✨ Your task list is empty. Time to be productive! ✨</p>
          </div>
        ) : (
          <div className="list-group">
            {tasks.map((task) => (
              <div
                key={task.id}
                className={`list-group-item d-flex justify-content-between align-items-center ${
                  darkMode ? 'dark-item' : ''
                }`}
              >
                <div className="d-flex align-items-center flex-grow-1">
                  <input
                    type="checkbox"
                    className="form-check-input me-3"
                    checked={task.completed}
                    onChange={() => toggleTask(task.id)}
                  />
                  <span 
                    style={{ 
                      textDecoration: task.completed ? 'line-through' : 'none',
                      opacity: task.completed ? 0.7 : 1
                    }}
                  >
                    {task.title}
                  </span>
                  <span className={`badge bg-${priorityColors[task.priority]} ms-2`}>
                    {task.priority}
                  </span>
                </div>
                <button
                  className="btn btn-outline-danger btn-sm ms-3"
                  onClick={() => deleteTask(task.id)}
                >
                  <span aria-hidden="true">×</span>
                </button>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App; 