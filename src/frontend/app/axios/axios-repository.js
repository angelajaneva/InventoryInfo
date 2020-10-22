import axios from './custom-axios'

const service = {

    getCategories: () => {
        return axios.get("/api/category")
    }
};

export default service;