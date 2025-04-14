import React from 'react';
import {
  Box,
  Typography,
  Paper,
  Avatar,
  Grid,
  TextField,
  Button,
} from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';

function Profile() {
  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Profile
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', mb: 4 }}>
          <Avatar
            sx={{ width: 100, height: 100, mb: 2 }}
          >
            <PersonIcon sx={{ fontSize: 60 }} />
          </Avatar>
          <Typography variant="h5">John Doe</Typography>
          <Typography color="textSecondary">john.doe@example.com</Typography>
        </Box>
        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="First Name"
              defaultValue="John"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Last Name"
              defaultValue="Doe"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Email"
              defaultValue="john.doe@example.com"
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Bio"
              multiline
              rows={4}
              defaultValue="Productivity enthusiast and task management expert."
              variant="outlined"
            />
          </Grid>
          <Grid item xs={12}>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 2 }}>
              <Button variant="outlined">Cancel</Button>
              <Button variant="contained">Save Changes</Button>
            </Box>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}

export default Profile; 