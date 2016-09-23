#!/bin/bash
# Installs software for docs.

DOCS=$(readlink -f $(dirname ${BASH_SOURCE[0]}))

apt-get -qq update
apt-get -qq install -y \
    graphviz \
    python-pip \
    python-virtualenv

(virtualenv -p /usr/bin/python2 ${DOCS}/venv && \
 source ${DOCS}/venv/bin/activate && \
 pip install -r requirements.txt
 deactivate)
