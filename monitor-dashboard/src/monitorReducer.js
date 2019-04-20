import { GET_ONLINE_SERVICES_STARTED, GET_ONLINE_SERVICES_SUCCESS, GET_ONLINE_SERVICES_FAILED } from "./actions";
import { GET_REQUESTS_PER_SERVICE_STARTED, GET_REQUESTS_PER_SERVICE_SUCCESS, GET_REQUESTS_PER_SERVICE_FAILED } from "./actions";

const monitorReducer = (
    state = {
        onlineServices: [],
        requestsPerService: []
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

            default:
                return state;
        }
    }

export default monitorReducer;