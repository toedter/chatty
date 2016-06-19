import {bootstrap} from '@angular/platform-browser-dynamic';
import {provide} from '@angular/core';
import {ROUTER_PROVIDERS} from '@angular/router';
import {HTTP_PROVIDERS} from '@angular/http';
import {ChattyComponent} from './ChattyComponent';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';

bootstrap(ChattyComponent, [HTTP_PROVIDERS, ROUTER_PROVIDERS, provide(LocationStrategy, {useClass: HashLocationStrategy})]);
