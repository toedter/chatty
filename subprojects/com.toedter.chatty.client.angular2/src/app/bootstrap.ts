import {bootstrap} from '@angular/platform-browser-dynamic';
import {provide} from '@angular/core';
import {HTTP_PROVIDERS} from '@angular/http';
import {ChattyComponent} from './ChattyComponent';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import { APP_ROUTER_PROVIDERS } from './ChattyRoutes';

bootstrap(ChattyComponent, [HTTP_PROVIDERS, APP_ROUTER_PROVIDERS, provide(LocationStrategy, {useClass: HashLocationStrategy})]);
