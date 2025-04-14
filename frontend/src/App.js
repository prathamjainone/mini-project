import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Tasks from './pages/Tasks';
import Navigation from './components/Navigation';

function App() {
  return (
    <>
      <Navigation />
      <Routes>
        <Route path="/tasks" element={<Tasks />} />
        <Route path="/" element={<Navigate to="/tasks" />} />
      </Routes>
    </>
  );
}

export default App; 