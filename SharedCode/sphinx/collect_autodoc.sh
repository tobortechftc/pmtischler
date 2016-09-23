#!/bin/bash
# Collects autodoc sources for javasphinx.

DOCS=$(readlink -f $(dirname ${BASH_SOURCE[0]}))

javasphinx-apidoc -f -o ${DOCS}/javasphinx/ ${DOCS}/../src/main
