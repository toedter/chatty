declare module Atmosphere {
    interface Response {

        status: number;
        reasonPhrase: string;
        responseBody: string;
        messages: string[];
        headers: string[];
        state: string;
        transport: string;
        error: string;
        request: Request;
        partialMessage: string;
        errorHandled: boolean;
        closedByClientTimeout: boolean;
    }

    interface Request {
        onError?: (response:Response) => any;
        onClose?:  (response:Response)  => any;
        onOpen?:  (response:Response)  => any;
        onMessage?:  (response:Response)  => any;
        onReopen?:  (request:Request, response:Response) => any;
        onReconnect?:  (request:Request, response:Response)  => any;
        onMessagePublished?:  (response:Response)  => any;
        onTransportFailure?:  (reason:string, response:Response)  => any;
        onLocalMessage?:  (request:Request) => any;
        onFailureToReconnect?:  (request, response:Response) => any;
        onClientTimeout?: (request:Request) => any;
    }

    interface Socket {
        subscribe?: (request:Request) => any;
    }
}

declare var atmosphere:Atmosphere.Socket;

