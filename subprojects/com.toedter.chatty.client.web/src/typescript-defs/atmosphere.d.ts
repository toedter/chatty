// Type definitions for Atmpsphere 2.1.6
// by Jeanfrancois Arcand, see
// https://github.com/Atmosphere/atmosphere-javascript
// TypeScript Definitions by Kai Toedter (kai@toedter.com)
// TypeScript Definitions are licensed under MIT license, see see http://toedter.mit-license.org/

declare module Atmosphere {
    interface Atmosphere {
        subscribe?: (string?, callback?:Function, request?:Request) => any;
        unsubscribe?: () => any;

        // Which of the below is API

//        unsubscribeUrl?: (url:string) => any;
//        addCallback?: (callback:Function) => any;


//        onError?: (response?:Response) => any;
//        onClose?:  (response?:Response)  => any;
//        onOpen?:  (response?:Response)  => any;
//        onMessage?:  (response?:Response)  => any;
//        onReopen?:  (request?:Request, response?:Response) => any;
//        onReconnect?:  (request?:Request, response?:Response)  => any;
//        onMessagePublished?:  (response?:Response)  => any;
//        onTransportFailure?:  (reason?:string, response?:Response)  => any;
//        onLocalMessage?:  (request?:Request) => any;
//        onFailureToReconnect?:  (request?:Request, response?:Response) => any;
//        onClientTimeout?: (request?:Request) => any;
    }

    interface Response {
        status?: number;
        reasonPhrase?: string;
        responseBody?: string;
        messages?: string[];
        headers?: string[];
        state?: string;
        transport?: string;
        error?: string;
        request?: Request;
        partialMessage?: string;
        errorHandled?: boolean;
        closedByClientTimeout?: boolean;
    }

    interface Request {
        // Which of the below is API?
        timeout?: number;
        method?: string;
        headers?: any;
        contentType?: string;
        callback?: Function;
        url?: string;
        data?: string;
        suspend?: boolean;
        maxRequest?: number;
        reconnect?: boolean;
        maxStreamingLength?: number;
        lastIndex?: number;
        logLevel?: string;
        requestCount?: number;
        fallbackMethod?: string;
        fallbackTransport?: string;
        transport?: string;
        webSocketImpl?: any;
        webSocketBinaryType?: any;
        dispatchUrl?: string;
        webSocketPathDelimiter?: string;
        enableXDR?: boolean;
        rewriteURL?: boolean;
        attachHeadersAsQueryString?: boolean;
        executeCallbackBeforeReconnect?: boolean;
        readyState?: number;
        lastTimestamp?: number;
        withCredentials?: boolean;
        trackMessageLength?: boolean;
        messageDelimiter?: string;
        connectTimeout?: number;
        reconnectInterval?: number;
        dropHeaders?: boolean;
        uuid?: number;
        async?: boolean;
        shared?: boolean;
        readResponsesHeaders?: boolean;
        maxReconnectOnClose?: number;
        enableProtocol?: boolean;
        pollingInterval ?: number;

        onError?: (response?:Response) => any;
        onClose?:  (response?:Response)  => any;
        onOpen?:  (response?:Response)  => any;
        onMessage?:  (response:Response)  => any;
        onReopen?:  (request?:Request, response?:Response) => any;
        onReconnect?:  (request?:Request, response?:Response)  => any;
        onMessagePublished?:  (response?:Response)  => any;
        onTransportFailure?:  (reason?:string, response?:Response)  => any;
        onLocalMessage?:  (request?:Request) => any;
        onFailureToReconnect?:  (request?:Request, response?:Response) => any;
        onClientTimeout?: (request?:Request) => any;
    }

    interface Socket {
        subscribe?: (options:Request) => any;
        execute?: () => any;
        close?: () => any;
        disconnect?: () => any;
        getUrl?: () => string;
        push?: (message:string, dispatchUrl?:string) => any;
        getUUID?: () => any;
        pushLocal?: (message:string) => any;
        enableProtocol?: (message:string) => any;
    }

    interface Util {
        // is Util supposed to be Atmosphere API?
        now:  ()  => number;
        parseHeaders:  (headerString:string) => any;
        isArray:  (array:any[])  => boolean;
        inArray:  (elem:any, array:any[])  => boolean;
        isBinary:  (data:any)  => boolean;
        isFunction:  (fn:Function)  => boolean;
        getAbsoluteURL:  (url:string)  => string;
        prepareURL:  (url:string)  => string;
        trim:  (str:string)  => string;
        param:  (params:string[])  => string;
        storage:  ()  => boolean;
        iterate:  (fn:Function, interval:number)  => Function;
        each:  (obj?:any, callback?:Function, args?:any)  => any;
        extend:  (target:any)  => any;
        on:  (elem:any, type:any, fn:Function)  => any;
        off:  (elem:any, type:any, fn:Function)  => any;
        log:  (level:string, args:any)  => any;
        warn:  ()  => any;
        info:  ()  => any;
        debug:  ()  => any;
        error:  ()  => any;
        xhr:  ()  => any;
        parseJSON:  (data:string)  => any;
        stringifyJSON:  (value:any)  => any;
        checkCORSSupport:  ()  => any;
    }
}

declare var atmosphere:Atmosphere.Atmosphere;


