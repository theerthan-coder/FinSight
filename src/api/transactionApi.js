import axiosInstance from './axiosInstance';

export const addTransaction = async (data) => {
  const res = await axiosInstance.post('/transaction/addt', data);
  return res.data.data;
};

export const getTransactions = async (userId) => {
  const res = await axiosInstance.get(`/transaction/user/${userId}`);
  return res.data.data;
};

export const updateTransaction = async (id, data) => {
  const res = await axiosInstance.put(`/transaction/update/transaction-id/${id}`, data);
  return res.data.data;
};

export const deleteTransaction = async (id) => {
  const res = await axiosInstance.delete(`/transaction/delete/transaction-id/${id}`);
  return res.data.data;
};
