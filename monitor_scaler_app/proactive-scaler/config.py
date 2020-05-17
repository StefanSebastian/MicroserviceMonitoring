monitor_window=60 # seconds
monitor_hist=8 # intervals
microservice_name='microservice1'
sla_threshold=18
services_url='http://localhost:8080/onlineservices'
scaleup_url='http://localhost:8081/scaleup?microserviceName=microservice1'
scaledown_url='http://localhost:8081/scaledown?microserviceName=microservice1&pid={}'

retrain_window=6000 # seconds