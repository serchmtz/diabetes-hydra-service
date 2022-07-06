# Diabetes Hydra Service

This project implements an Hypermedia RESTful API that conforms [Hydra Core
Vocabulary](http://www.hydra-cg.com/spec/latest/core/)

The project was part of my internship program that has the goal to develop a
semantic web service that take avantage of a large OWL onthology. To accomplish
this, I used linked data techologies such as JSON-LD and Hydra Core. The library
used to expose the API is a modified version of
[levanzo](https://github.com/antoniogarrote/levanzo). I only added some JSON-LD
1.1 features (external context)
[here](https://github.com/serchmtz/levanzo/tree/feature/external-context).

## Explore the API

The API is hosted in Heroku and can be explored using the [Hydra Client](https://diabetes-hydra-client.vercel.app/)
developed to that propurse. The GitHub repo of the client is [here](https://github.com/serchmtz/diabetes-hydra-client)
