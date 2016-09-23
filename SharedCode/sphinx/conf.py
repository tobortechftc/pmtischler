import sys
import os
import shlex

extensions = [
    'javasphinx',
    'sphinx.ext.autodoc',
    'sphinx.ext.graphviz',
    'sphinx.ext.napoleon',
]

templates_path = ['_templates']
source_suffix = '.rst'
master_doc = 'index'

project = u'pmtischler/ftc_app'
copyright = u'2016, Phillip Tischler (pmtischler)'
author = u'Phillip Tischler (pmtischler)'

version = '2016'
release = '2016.1'

language = None

exclude_patterns = ['README.rst', '_build', 'venv']

pygments_style = 'sphinx'
todo_include_todos = False

javadoc_url_map = {
    'com.github.pmtischler': ('javasphinx/', 'sphinx')
}

# Mock out client dependencies.
autodoc_mock_imports = []

html_theme = 'sphinx_rtd_theme'

htmlhelp_basename = 'FirstFtcAppPmtischler'

latex_elements = {
    'papersize': 'a4paper',
    'pointsize': '11pt',
}

# Grouping the document tree into LaTeX files. List of tuples
# (source start file, target name, title,
#  author, documentclass [howto, manual, or own class]).
latex_documents = [
    (master_doc, 'FirstFtcAppPmtischler.tex',
     u'Documentation for Customization of FIRST FTC App for Improved Development.',
     'manual'),
]

man_pages = [
    (master_doc, 'firstftcapppmtischler',
     u'Documentation for Customization of FIRST FTC App for Improved Development.', [author], 1)
]

texinfo_documents = [
    (master_doc, 'FirstFtcAppPmtischler',
     u'pmtischler/ftc_app', author,
     'FirstFtcAppPmtischler', 'Documentation for Customization of FIRST FTC App for Improved Development.',
     'Miscellaneous'),
]
