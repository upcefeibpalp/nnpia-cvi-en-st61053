import { createAsyncThunk } from "@reduxjs/toolkit";
import { IUser } from "../types";

export const getUsers = createAsyncThunk<IUser[]>(
    "user/getUsers",
    async (_, { rejectWithValue }) => {
        try {
            const response = await fetch("http://localhost:8080/api/v1/users");

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data: IUser[] = await response.json();
            return data;
        } catch (error: any) {
            return rejectWithValue(error.message || "Failed to fetch users");
        }
    }
);
