import {Component, OnInit} from "@angular/core";
import {About} from "./about";
import {AboutService} from "./about.service";

@Component({
    selector: 'about',
    templateUrl: 'about.component.html',
    styleUrls: ['about.component.css']
})
export class AboutComponent implements OnInit{
    version: string | undefined;
    timestamp: string | undefined;

    constructor(private aboutService: AboutService) {
    }

    ngOnInit() {
        this.aboutService.getAbout()
            .subscribe((about: About) => {
                this.timestamp = about.timeStamp;
                this.version = about.version;
            }),
            () => console.error('AboutComponent: cannot get about from AboutService');
    }

}
