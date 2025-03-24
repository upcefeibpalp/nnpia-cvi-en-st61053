import { createSlice } from '@reduxjs/toolkit'
import { IUser, IUserState } from './types'
import { getUsers } from './api/getUsers';


// Define the initial state using that type
const initialState: IUserState = {
    users: [],
    loading: false
}

export const userSlice = createSlice({
    name: 'user',
    // `createSlice` will infer the state type from the `initialState` argument
    initialState,
    reducers: {

    },
    selectors: {
        getUserList: (state) => state.users
    },
    extraReducers: (builder) => {
        builder
            .addCase(getUsers.pending, (state) => {
                state.loading = true;
            })
            .addCase(getUsers.fulfilled, (state, action: { payload: IUser[] }) => {
                state.loading = false;
                state.users = action.payload;
            })
            .addCase(getUsers.rejected, (state) => {
                state.loading = false;
            });
    }
})

export const {
    getUserList
} = userSlice.selectors


export default userSlice.reducer