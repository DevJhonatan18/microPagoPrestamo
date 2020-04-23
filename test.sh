#!/bin/bash



curl -X POST 'localhost:8010/v1/paymentEvent' -H 'Content-Type:application/json' --data '{"nroprestamo": 1 , "importeCuota": 293.60 , "cuota": 2}' | python -m json.tool
