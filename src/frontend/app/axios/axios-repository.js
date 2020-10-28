import axios from './custom-axios'

const service = {

    getCategories: () => {
        return axios.get("/api/category")
    },
    getGasses: () => {
        return axios.get("/api/gas")
    },
    getYears: () => {
        return axios.get("/api/analysis")
    }
};

export default service;