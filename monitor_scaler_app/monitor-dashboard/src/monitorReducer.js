import { GET_ONLINE_SERVICES_STARTED, GET_ONLINE_SERVICES_SUCCESS, GET_ONLINE_SERVICES_FAILED } from "./actions";
import { GET_REQUESTS_PER_SERVICE_STARTED, GET_REQUESTS_PER_SERVICE_SUCCESS, GET_REQUESTS_PER_SERVICE_FAILED } from "./actions";
import { GET_AVERAGE_REQUEST_TIMES_STARTED, GET_AVERAGE_REQUEST_TIMES_SUCCESS, GET_AVERAGE_REQUEST_TIMES_FAILED } from "./actions";

const monitorReducer = (
    state = {
        onlineServices: [],
        requestsPerService: [],
        averageRequestTimes: []
    }, 
    action) => {
        switch(action.type) {
            case GET_ONLINE_SERVICES_STARTED:
                return {...state}
            case GET_ONLINE_SERVICES_FAILED:
                return {...state}
            case GET_ONLINE_SERVICES_SUCCESS:
                return {...state, onlineServices: action.services}

            case GET_REQUESTS_PER_SERVICE_STARTED:
                return {...state}
            case GET_REQUESTS_PER_SERVICE_FAILED:
                return {...state}
            case GET_REQUESTS_PER_SERVICE_SUCCESS:
                return {...state, requestsPerService: action.requestsPerService }

            case GET_AVERAGE_REQUEST_TIMES_STARTED:
                return {...state}
            case GET_AVERAGE_REQUEST_TIMES_FAILED:
                return {...state}
            case GET_AVERAGE_REQUEST_TIMES_SUCCESS:
                return {...state, averageRequestTimes: action.averageRequestTimes}

            default:
                return state;
        }
    }

export default monitorReducer;