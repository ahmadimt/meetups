# Reactive Spring UI

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 6.0.8.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

## Known Issues

if you face below issue:
`
ERROR in ./node_modules/eventSource/lib/eventsource.js
Module not found: Error: Can't resolve 'http' in '/meetups/reactive-spring/front-end/node_modules/eventSource/lib'
ERROR in ./node_modules/eventSource/lib/eventsource.js
`

Go to `\node_modules\@angular-devkit\build-angular\src\angular-cli-files\models\webpack-configs\browser.js` and replace `node` key with following:
`node: {
   "fs": "empty",
   "global": true,
   "crypto": "empty",
   "tls": "empty",
   "net": "empty",
   "process": true,
   "module": false,
   "clearImmediate": false,
   "setImmediate": false
 }`
 
 More details can be found at https://github.com/angular/devkit/issues/967


