# CornFrontend

Frontend for the Corn project. Application has been made using Angular framework. We are using modern web technologies
to provide best experience possible. This app has been made a PWA (Progressive Web App) so it can be installed on your
device and used as a native app. We are trying to reach the best lighthouse score possible.

## How to run it

1. Using npm first install all dependencies

```bash
npm install
```

And then run the app using

```bash
npm start
```

2. You can also run the app using docker. First build the image

```bash
docker build -f Dockerfile.prod -t corn-frontend .
```

And then run the container

```bash
docker run -p 4200:80 corn-frontend
```

## Technology stack

* Angular 17,
* Angular Material,
* Angular PWA,
* Keycloak Service,
* Nginx,
* NgIcons,
* TypeScript,
* Docker,
* Tailwind CSS,
* Scss
