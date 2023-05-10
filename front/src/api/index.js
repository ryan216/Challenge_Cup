import axios from "axios";
let baseurl = "http://172.16.10.100:9000";
axios.defaults.withCredentials = true;

// //添加一个请求拦截器,在请求头部添加token
// axios.interceptors.request.use(
//     config => {
//       if (window.localStorage.getItem('access-token')) {
//         config.headers['access-token'] = window.localStorage.getItem('access-token');
//       }
//       store.state.loading = true
//       return config
//     },
//     error => {
//       return Promise.reject(error)
//     }
//   );
// 添加一个响应拦截器
axios.interceptors.response.use(function (res) {
  return res;
}, error => {
  return Promise.reject(error);
});
export default {
    baseurl
}

export const POST = (url, params) =>{
    return axios.post(`${baseurl}${url}`, params).then(res => res.data);
}

export const GET = (url, params) =>{
    return axios.get(`${baseurl}${url}`, {params: params}).then(res => res.data);
}