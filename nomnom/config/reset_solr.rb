require 'solr'
solr = Solr::Connection.new("http://localhost:8983/solr")
solr.delete_by_query('*:*')
solr.commit
