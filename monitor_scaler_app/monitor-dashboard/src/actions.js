import { handleError } from "./utils";

export const GET_ONLINE_SERVICES_STARTED = 'GET_ONLINE_SERVICES_STARTED';
export const GET_ONLINE_SERVICES_SUCCESS = 'GET_ONLINE_SERVICES_SUCCESS';
export const GET_ONLINE_SERVICES_FAILED = 'GET_ONLINE_SERVICES_FAILED';

export const GET_REQUESTS_PER_SERVICE_STARTED = 'GET_REQUESTS_PER_SERVICE_STARTED';
export const GET_REQUESTS_PER_SERVICE_SUCCESS = 'GET_REQUESTS_PER_SERVICE_SUCCESS';
export const GET_REQUESTS_PER_SERVICE_FAILED = 'GET_REQUESTS_PER_SERVICE_FAILED';

export const GET_AVERAGE_REQUEST_TIMES_STARTED = 'GET_AVERAGE_REQUEST_TIMES_STARTED';
export const GET_AVERAGE_REQUEST_TIMES_SUCCESS = 'GET_AVERAGE_REQUEST_TIMES_SUCCESS';
export const GET_AVERAGE_REQUEST_TIMES_FAILED = 'GET_AVERAGE_REQUEST_TIMES_FAILED';

export const GET_SLA_STATS_STARTED = 'GET_SLA_STATS_STARTED';
export const GET_SLA_STATS_SUCCESS = 'GET_SLA_STATS_SUCCESS';
export const GET_SLA_STATS_FAILED = 'GET_SLA_STATS_FAILED';

export function getAverageRequestTimesStarted() {
    return {
        type: GET_AVERAGE_REQUEST_TIMES_STARTED
    }
}

export function getAverageRequestTimesFailed() {
    return {
        type: GET_AVERAGE_REQUEST_TIMES_FAILED
    }
}

export function getAverageRequestTimesSuccess(json) {
    return {
        type: GET_AVERAGE_REQUEST_TIMES_SUCCESS,
        averageRequestTimes: json
    }
}

export function getAverageRequestTimesPerService() {
    return dispatch => {
        dispatch(getAverageRequestTimesStarted())
        return fetch(`http://localhost:8080/avgtimes`)
        .then(handleError)
        .then(json => dispatch(getAverageRequestTimesSuccess(json)))
        .catch(function(error) {dispatch(getAverageRequestTimesFailed(error.message))})
    }
}

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

export function getSLAStatsStarted() {
    return {
        type: GET_SLA_STATS_STARTED
    }
}

export function getSLAStatsSuccess(json) {
    return {
        type: GET_SLA_STATS_SUCCESS,
        slaStats: json
    }
}

export function getSLAStatsFailed() {
    return {
        type: GET_SLA_STATS_FAILED
    }
}

export function getSLAStats() {
    return dispatch => {
        dispatch(getSLAStatsStarted())
        return fetch(`http://localhost:8080/slastats`)
        .then(handleError)
        .then(json => dispatch(getSLAStatsSuccess(json)))
        .catch(function(error) {dispatch(getSLAStatsFailed(error.message))})
    }
}