import api from './api';

/**
 * Service for interacting with task-related API endpoints
 */
const taskService = {
  /**
   * Get all tasks
   * @returns {Promise} Promise with the list of tasks
   */
  getAllTasks: async () => {
    try {
      const response = await api.get('/tasks');
      return response.data;
    } catch (error) {
      console.error('Error fetching tasks:', error);
      throw error;
    }
  },

  /**
   * Get a task by ID
   * @param {number} id - Task ID
   * @returns {Promise} Promise with the task data
   */
  getTaskById: async (id) => {
    try {
      const response = await api.get(`/tasks/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching task with ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Create a new task
   * @param {Object} task - Task data
   * @returns {Promise} Promise with the created task
   */
  createTask: async (task) => {
    try {
      const response = await api.post('/tasks', task);
      return response.data;
    } catch (error) {
      console.error('Error creating task:', error);
      throw error;
    }
  },

  /**
   * Update a task's title
   * @param {number} id - Task ID
   * @param {string} title - New title
   * @returns {Promise} Promise with the updated task
   */
  updateTaskTitle: async (id, title) => {
    try {
      const response = await api.put(`/tasks/${id}/title`, null, {
        params: { title }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating task title for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a task's description
   * @param {number} id - Task ID
   * @param {string} description - New description
   * @returns {Promise} Promise with the updated task
   */
  updateTaskDescription: async (id, description) => {
    try {
      const response = await api.put(`/tasks/${id}/description`, null, {
        params: { description }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating task description for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a task's priority
   * @param {number} id - Task ID
   * @param {string} priority - New priority (LOW, MEDIUM, HIGH)
   * @returns {Promise} Promise with the updated task
   */
  updateTaskPriority: async (id, priority) => {
    try {
      const response = await api.put(`/tasks/${id}/priority`, null, {
        params: { priority }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating task priority for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Toggle a task's completion status
   * @param {number} id - Task ID
   * @returns {Promise} Promise with the updated task
   */
  toggleTaskCompletion: async (id) => {
    try {
      const response = await api.put(`/tasks/${id}/toggle`);
      return response.data;
    } catch (error) {
      console.error(`Error toggling task completion for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Delete a task
   * @param {number} id - Task ID
   * @returns {Promise} Promise with the deletion result
   */
  deleteTask: async (id) => {
    try {
      const response = await api.delete(`/tasks/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting task with ID ${id}:`, error);
      throw error;
    }
  }
};

export default taskService; 