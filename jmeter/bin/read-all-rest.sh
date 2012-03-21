#!/bin/bash
NODE_COUNT=${NODE_COUNT:=10000}

# switch to test "home" directory
OWD=$PWD
ABSPATH="$(cd "${0%/*}" 2>/dev/null; echo "$PWD"/"${0##*/}")"
DIRPATH=`dirname "$ABSPATH"`
cd $DIRPATH/..

# run test
jakarta-jmeter-2.5/bin/jmeter -n -t plans/REST-Read-All.jmx -Jnode_count=${NODE_COUNT}

cd $OWD
