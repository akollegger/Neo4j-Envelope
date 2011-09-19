#!/bin/bash

# algorithm params
RMAT_SCALE=${RMAT_SCALE:=8}

#logging config
SCRIPT=`basename $0`
RESULT_LOG="../results/${SCRIPT}.log"

# build the URL
HOST_UNDER_TEST="localhost"
HOST_PORT="7474"
ENDPOINT="lab/env/rmat/${RMAT_SCALE}"

echo "timed curl POST /${ENDPOINT}" >${RESULT_LOG}
(time curl --verbose -X POST -H Accept:application/json -H Content-Type:application/json -v http://${HOST_UNDER_TEST}:${HOST_PORT}/${ENDPOINT}) 2>${RESULT_LOG}