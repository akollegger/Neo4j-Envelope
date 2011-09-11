#!/bin/bash
RESULT_LOG="../results/create-linked-list"
HOST_UNDER_TEST="localhost"
HOST_PORT="7474"
COUNT="100000"
BATCH="10000"
ENDPOINT="lab/env/linked-list/${COUNT}/${BATCH}"

echo "timed curl POST /${ENDPOINT}" >${RESULT_LOG}
(time curl --verbose -X POST -H Accept:application/json -H Content-Type:application/json -v http://${HOST_UNDER_TEST}:${HOST_PORT}/${ENDPOINT}) 2>${RESULT_LOG}
