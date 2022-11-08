import {axiosPrivate} from "../api/axios";

const create = async (data) => {
    return await axiosPrivate.post(`/alerts`, data);
}

const PriceAlertService = {
    create
}

export default PriceAlertService;