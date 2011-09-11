#!/bin/bash
RESULT_LOG="../results/create-disc-graph"
HOST_UNDER_TEST="localhost"
COUNT="100000"
BATCH="10000"
ENDPOINT="lab/env/max-write/${COUNT}"

echo "timed curl POST ${ENDPOINT}" >${RESULT_LOG}
(time curl -X POST -H Accept:application/json -H Content-Type:application/json -v http://${HOST_UNDER_TEST}:7474/${ENDPOINT}) 2>${RESULT_LOG}
