#!/bin/bash

# operation parameters
NODE_COUNT=${NODE_COUNT:=10000}
PATH_DEPTH=${PATH_DEPTH:=4}

#  build URL of the operation
HOST_UNDER_TEST="localhost"
HOST_PORT="7474"
ENDPOINT="/lab/env/any-path/${NODE_COUNT}/${PATH_DEPTH}"
OPS_URL="http://${HOST_UNDER_TEST}:${HOST_PORT}${ENDPOINT}"

# logging
SCRIPT=`basename $0`
RESULT_LOG="../results/${SCRIPT}.log"
JSON_RESULT="../results/${SCRIPT}.json"

# run test
echo "Script: ${SCRIPT}"  >  ${RESULT_LOG}
echo "URL:    ${OPS_URL}" >> ${RESULT_LOG} 
echo "" >> ${RESULT_LOG}

(time curl --silent -H Accept:application/json -H Content-Type:application/json -v ${OPS_URL} > ${JSON_RESULT} ) 2>>${RESULT_LOG}

echo "JSON: ${json}" >> ${RESULT_LOG}
cat ${JSON_RESULT} >> ${RESULT_LOG}

