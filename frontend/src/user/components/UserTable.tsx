import { Box } from '@mui/material';
import React from 'react'
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useAppSelector } from '../../app/hooks';
import { getUserList } from '../userSlice';

const UserTable = () => {

    const columns: GridColDef[] = [
        { field: 'id', headerName: 'ID', width: 300 },
        {
            field: 'email',
            headerName: 'Email',
            width: 150,
            editable: true,
        },
        {
            field: 'password',
            headerName: 'Password',
            width: 150,
            editable: true,
        },
    ];

    const rows = useAppSelector(getUserList);
    const loading = useAppSelector((state) => state.user.loading);

    return (
        <Box sx={{ height: 400, width: '100%' }}>
            <DataGrid
                loading={loading}
                rows={rows}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 5,
                        },
                    },
                }}
                pageSizeOptions={[5]}
                checkboxSelection
                disableRowSelectionOnClick
            />
        </Box>
    )
}

export default UserTable