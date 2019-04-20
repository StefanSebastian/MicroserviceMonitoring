
export function handleError(response) {
    if (response.status === 500) {
        return response.json().then((json) => {
            throw new Error(json.message)
        })
    } else if (response.status === 400) {
        throw new Error("Invalid entity")
    } else {
        return response.json();
    }
}