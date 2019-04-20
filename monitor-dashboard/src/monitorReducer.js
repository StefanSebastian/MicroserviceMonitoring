import { GET_ONLINE_SERVICES_STARTED, GET_ONLINE_SERVICES_SUCCESS, GET_ONLINE_SERVICES_FAILED } from "./actions";

const monitorReducer = (
    state = {
        onlineServices: []
    }, 
    action) => {
        switch(action.type) {
            case GET_ONLINE_SERVICES_STARTED:
                return {...state}
            case GET_ONLINE_SERVICES_FAILED:
                return {...state}
            case GET_ONLINE_SERVICES_SUCCESS:
                return {...state, onlineServices: action.services}

            default:
                return state;
        }
    }

export default monitorReducer;