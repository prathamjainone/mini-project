import api from './api';

/**
 * Service for interacting with user-related API endpoints
 */
const userService = {
  /**
   * Get all users
   * @returns {Promise} Promise with the list of users
   */
  getAllUsers: async () => {
    try {
      const response = await api.get('/users');
      return response.data;
    } catch (error) {
      console.error('Error fetching users:', error);
      throw error;
    }
  },

  /**
   * Get a user by ID
   * @param {number} id - User ID
   * @returns {Promise} Promise with the user data
   */
  getUserById: async (id) => {
    try {
      const response = await api.get(`/users/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching user with ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Get a user by username
   * @param {string} username - Username
   * @returns {Promise} Promise with the user data
   */
  getUserByUsername: async (username) => {
    try {
      const response = await api.get('/users/byUsername', {
        params: { username }
      });
      return response.data;
    } catch (error) {
      console.error(`Error fetching user with username ${username}:`, error);
      throw error;
    }
  },

  /**
   * Create a new user
   * @param {Object} user - User data
   * @returns {Promise} Promise with the created user
   */
  createUser: async (user) => {
    try {
      const response = await api.post('/users', user);
      return response.data;
    } catch (error) {
      console.error('Error creating user:', error);
      throw error;
    }
  },

  /**
   * Update a user's full name
   * @param {number} id - User ID
   * @param {string} fullName - New full name
   * @returns {Promise} Promise with the updated user
   */
  updateFullName: async (id, fullName) => {
    try {
      const response = await api.put(`/users/${id}/fullName`, null, {
        params: { newFullName: fullName }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating user full name for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a user's email
   * @param {number} id - User ID
   * @param {string} email - New email
   * @returns {Promise} Promise with the updated user
   */
  updateEmail: async (id, email) => {
    try {
      const response = await api.put(`/users/${id}/email`, null, {
        params: { newEmail: email }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating user email for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a user's username
   * @param {number} id - User ID
   * @param {string} username - New username
   * @returns {Promise} Promise with the updated user
   */
  updateUsername: async (id, username) => {
    try {
      const response = await api.put(`/users/${id}/username`, null, {
        params: { newUsername: username }
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating user username for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a user's password
   * @param {number} id - User ID
   * @param {string} currentPassword - Current password
   * @param {string} newPassword - New password
   * @returns {Promise} Promise with the updated user
   */
  updatePassword: async (id, currentPassword, newPassword) => {
    try {
      const response = await api.put(`/users/${id}/password`, {
        currentPassword,
        newPassword
      });
      return response.data;
    } catch (error) {
      console.error(`Error updating user password for ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Update a user's profile information
   * @param {number} id - User ID
   * @param {Object} userData - User data to update
   * @returns {Promise} Promise with the updated user
   */
  updateUser: async (id, userData) => {
    try {
      const response = await api.put(`/users/${id}`, userData);
      return response.data;
    } catch (error) {
      console.error(`Error updating user with ID ${id}:`, error);
      throw error;
    }
  },

  /**
   * Delete a user
   * @param {number} id - User ID
   * @returns {Promise} Promise with the deletion result
   */
  deleteUser: async (id) => {
    try {
      const response = await api.delete(`/users/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting user with ID ${id}:`, error);
      throw error;
    }
  }
};

export default userService; 