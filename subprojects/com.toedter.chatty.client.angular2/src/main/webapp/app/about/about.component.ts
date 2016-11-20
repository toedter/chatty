import {Component, OnInit} from "@angular/core";
import {About} from "./about";
import {AboutService} from "./about.service";

@Component({
    selector: 'about',
    templateUrl: 'about.component.html',
    styleUrls: ['about.component.css']
})
export class AboutComponent implements OnInit{
    private version: string;
    private timestamp: string;

    constructor(private aboutService: AboutService) {
        console.log('AboutComponent constructor');
    }

    ngOnInit() {
        this.aboutService.getAbout()
            .subscribe((about: About) => {
                this.timestamp = about.timeStamp;
                this.version = about.version;
            }),
            error => console.error('AboutComponent: cannot get about from AboutService');
    }

}
