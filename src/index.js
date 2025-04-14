import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import { GoogleOAuthProvider } from '@react-oauth/google';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <GoogleOAuthProvider clientId="67972457312-k0667k3ee3dnj4nn4etjifk4l1i0c48q.apps.googleusercontent.com">
      {/* Get your Client ID from Google Cloud Console:
          1. Go to APIs & Services > Credentials
          2. Create OAuth 2.0 Client ID
          3. Application type: Web application
          4. Add origin: http://localhost:3000
          5. Copy the Client ID and paste it here */}
      <App />
    </GoogleOAuthProvider>
  </React.StrictMode>
); 