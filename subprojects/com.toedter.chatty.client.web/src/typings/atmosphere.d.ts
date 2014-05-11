// Type definitions for Atmosphere v2.1.5
// Project: https://github.com/Atmosphere/atmosphere-javascript
// Definitions by: Kai Toedter <https://github.com/toedter/>
// Definitions: https://github.com/borisyankov/DefinitelyTyped

// TypeScript Definitions are licensed under MIT license, see see http://toedter.mit-license.org/


declare module Atmosphere {
    interface Atmosphere {
        /**
         * The atmosphere api is a little bit weird here: the first parameter can either be
         * a URL string or a Request object. If it is a URL string, then the additional parameters are expected.
         */
        subscribe?: (url:any, callback?:Function, request?:Request) => Request;
        unsubscribe?: () => void;
        AtmosphereRequest:{};
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
        // Are all of the below attributes supposed to be API?
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
        pollingInterval?: number;

        onError?: (response?:Response) => void;
        onClose?:  (response?:Response)  => void;
        onOpen?:  (response?:Response)  => void;
        onMessage?:  (response:Response)  => void;
        onReopen?:  (request?:Request, response?:Response) => void;
        onReconnect?:  (request?:Request, response?:Response)  => void;
        onMessagePublished?:  (response?:Response)  => void;
        onTransportFailure?:  (reason?:string, response?:Response)  => void;
        onLocalMessage?:  (request?:Request) => void;
        onFailureToReconnect?:  (request?:Request, response?:Response) => void;
        onClientTimeout?: (request?:Request) => void;

        subscribe?: (options:Atmosphere.Request) => void;
        execute?: () => void;
        close?: () => void;
        disconnect?: () => void;
        getUrl?: () => string;
        push?: (message:string, dispatchUrl?:string) => void;
        getUUID?: () => void;
        pushLocal?: (message:string) => void;
    }
}

declare var atmosphere:Atmosphere.Atmosphere;


