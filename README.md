# Diabetes Hydra Service

This project implements a Hypermedia RESTful API that conforms the [Hydra Core
Vocabulary](http://www.hydra-cg.com/spec/latest/core/)

The project was part of my internship program, the goal was to develop a
semantic web service that will take avantage of a large OWL onthology. To accomplish
this, I used linked data techologies such as JSON-LD and Hydra Core and some for OWL processing ([tawny-owl](https://github.com/phillord/tawny-owl), [OWLAPI](https://github.com/owlcs/owlapi)).

To expose the API, I used a modified version of
[levanzo](https://github.com/antoniogarrote/levanzo). I only added some JSON-LD
1.1 features (external context)
[here](https://github.com/serchmtz/levanzo/tree/feature/external-context).

## Explore the API

The API is hosted on Heroku and can be explored using the [Hydra Client](https://diabetes-hydra-client.vercel.app/)
developed to that purpose. The GitHub repo of the client is [here](https://github.com/serchmtz/diabetes-hydra-client).
