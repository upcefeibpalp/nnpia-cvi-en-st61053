export interface IUserState {
    users: IUser[];
    loading: boolean;
}


export interface IUser {
    id: string;
    email: string;
    password: string;
}