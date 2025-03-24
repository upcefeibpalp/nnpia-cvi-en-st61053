import React, { useEffect } from 'react';
import User from './user/components/User';
import { getUsers } from './user/api/getUsers';
import { useAppDispatch } from './app/hooks';
import { Box, Typography } from '@mui/material';
import UserTable from './user/components/UserTable';

function App() {

  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getUsers());
  }, []);

  return (
    <Box sx={{
      height: 'calc(100vh - 4em)',
      display: 'flex',
      flexDirection: 'column',
      p: 4,
      gap: 2,
    }}>
      <Typography variant="h4">Users</Typography>
      <UserTable />
    </Box>
  );
}

export default App;
