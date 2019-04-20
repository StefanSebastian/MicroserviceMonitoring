import { handleError } from "./utils";

export const GET_ONLINE_SERVICES_STARTED = 'GET_ONLINE_SERVICES_STARTED';
export const GET_ONLINE_SERVICES_SUCCESS = 'GET_ONLINE_SERVICES_SUCCESS';
export const GET_ONLINE_SERVICES_FAILED = 'GET_ONLINE_SERVICES_FAILED';

export function getOnlineServiceStarted() {
    return {
        type: GET_ONLINE_SERVICES_STARTED
    }
}

export function getOnlineServicesSuccess(json) {
    return {
        type: GET_ONLINE_SERVICES_SUCCESS,
        services: json
    }
}

export function getOnlineServicesFailed() {
    return {
        type: GET_ONLINE_SERVICES_FAILED
    }
}

export function getOnlineServices() {
    return dispatch => {
        dispatch(getOnlineServiceStarted())
        return fetch(`http://localhost:8080/onlineservices`)
        .then(handleError)
        .then(json => dispatch(getOnlineServicesSuccess(json)))
        .catch(function(error) {dispatch(getOnlineServicesFailed(error.message))})
    }
}