# hydra-skills-service

This repository began life as a clone of Caliber's
[CategoryRepositoryService](https://github.com/revaturelabs/caliber/tree/MSA/CategoryRepositoryService),
using Caliber's .gitignore.  Then, Assignforce's
[skillService](https://github.com/revaturelabs/assignforce-ms-ecosystem/tree/skillService)
([skillService-development](https://github.com/revaturelabs/assignforce-ms-ecosystem/tree/skillService-development)
for the up-to-date work) was merged into it as this new, common Skills service.

The Skills service has services and a controller for CRUD operations between
SimpleSkill beans and the database, as well as for composing those simple
beans into complex Skill beans required by the front end.  Other services
handle messaging between the services, a request dispatcher, and the Panel
service.
