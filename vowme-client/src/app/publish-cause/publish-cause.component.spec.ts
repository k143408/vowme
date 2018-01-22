import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PublishCauseComponent } from './publish-cause.component';

describe('PublishCauseComponent', () => {
  let component: PublishCauseComponent;
  let fixture: ComponentFixture<PublishCauseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PublishCauseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PublishCauseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
