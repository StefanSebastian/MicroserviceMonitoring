import { handleError } from "./utils";

export const GET_ONLINE_SERVICES_STARTED = 'GET_ONLINE_SERVICES_STARTED';
export const GET_ONLINE_SERVICES_SUCCESS = 'GET_ONLINE_SERVICES_SUCCESS';
export const GET_ONLINE_SERVICES_FAILED = 'GET_ONLINE_SERVICES_FAILED';

export const GET_REQUESTS_PER_SERVICE_STARTED = 'GET_REQUESTS_PER_SERVICE_STARTED';
export const GET_REQUESTS_PER_SERVICE_SUCCESS = 'GET_REQUESTS_PER_SERVICE_SUCCESS';
export const GET_REQUESTS_PER_SERVICE_FAILED = 'GET_REQUESTS_PER_SERVICE_FAILED';

export function getRequestsPerServiceStarted() {
    return {
        type: GET_REQUESTS_PER_SERVICE_STARTED
    }
}

export function getRequestsPerServiceSuccess(json) {
    return {
        type: GET_REQUESTS_PER_SERVICE_SUCCESS,
        requestsPerService: json
    }
}

export function getRequestsPerServiceFailed() {
    return {
        type: GET_REQUESTS_PER_SERVICE_FAILED
    }
}

export function getRequestsPerService() {
    return dispatch => {
        dispatch(getRequestsPerServiceStarted())
        return fetch(`http://localhost:8080/nrrequests`)
        .then(handleError)
        .then(json => dispatch(getRequestsPerServiceSuccess(json)))
        .catch(function(error) {dispatch(getRequestsPerServiceFailed(error.message))})
    }
}

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