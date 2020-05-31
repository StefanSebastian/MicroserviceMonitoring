import { GET_ONLINE_SERVICES_STARTED, GET_ONLINE_SERVICES_SUCCESS, GET_ONLINE_SERVICES_FAILED } from "./actions";
import { GET_REQUESTS_PER_SERVICE_STARTED, GET_REQUESTS_PER_SERVICE_SUCCESS, GET_REQUESTS_PER_SERVICE_FAILED } from "./actions";
import { GET_AVERAGE_REQUEST_TIMES_STARTED, GET_AVERAGE_REQUEST_TIMES_SUCCESS, GET_AVERAGE_REQUEST_TIMES_FAILED } from "./actions";
import { GET_SLA_STATS_STARTED, GET_SLA_STATS_SUCCESS, GET_SLA_STATS_FAILED} from "./actions";
import { SCALEDOWN_STARTED, SCALEDOWN_FAILED, SCALEDOWN_SUCCESS, } from "./actions";
import { SCALEUP_STARTED, SCALEUP_FAILED, SCALEUP_SUCCESS} from "./actions";

const monitorReducer = (
    state = {
        onlineServices: [],
        requestsPerService: [],
        averageRequestTimes: [],
        slaStats: [],
        error: ""
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

            case GET_SLA_STATS_STARTED:
                return {...state}
            case GET_SLA_STATS_FAILED:
                return {...state}
            case GET_SLA_STATS_SUCCESS:
                return {...state, slaStats: action.slaStats}

            case SCALEDOWN_STARTED:
                return {...state, error: ""}
            case SCALEDOWN_FAILED:
                return {...state, error: "Scaledown failed"}
            case SCALEDOWN_SUCCESS:
                return {...state, error: ""}

            case SCALEUP_STARTED:
                return {...state, error: ""}
            case SCALEUP_FAILED:
                return {...state, error: "Scaleup failed"}
            case SCALEUP_SUCCESS:
                return {...state, error: ""}

            default:
                return state;
        }
    }

export default monitorReducer;