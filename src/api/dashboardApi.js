import axiosInstance from './axiosInstance';

export const getSummary = async (userId) => {
  const res = await axiosInstance.get(`/function/summary/user/${userId}`);
  return res.data.data;
};

export const getHealth = async (userId) => {
  const res = await axiosInstance.get(`/function/financial-health/${userId}`);
  return res.data.data;
};

export const getInsights = async (userId) => {
  const res = await axiosInstance.get(`/function/insights/${userId}`);
  return res.data.data;
};

export const getCategorySummary = async (userId) => {
  const res = await axiosInstance.get(`/function/category/user/${userId}`);
  return res.data.data;
};

export const getTransactionsByRange = async (userId, from, to) => {
  const res = await axiosInstance.get(`/function/range`, {
    params: { userId, from, to },
  });
  return res.data.data;
};
