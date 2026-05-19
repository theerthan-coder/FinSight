import axiosInstance from './axiosInstance';

export const loginUser = async (email, password) => {
  const res = await axiosInstance.post('/employee/secret', { email, password });
  return res.data.data; // { userId, name, email }
};

export const registerUser = async ({ name, email, password, phoneNumber }) => {
  const res = await axiosInstance.post('/users/paths/register', {
    name,
    email,
    password,
    phoneNumber,
  });
  return res.data;
};

export const updateUser = async (id, data) => {
  const res = await axiosInstance.put(`/users/paths/${id}`, data);
  return res.data.data;
};

export const deleteUser = async (id) => {
  const res = await axiosInstance.delete(`/users/paths/${id}`);
  return res.data.data;
};
