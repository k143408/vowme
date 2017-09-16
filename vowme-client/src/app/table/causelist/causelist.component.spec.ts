import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CauselistComponent } from './causelist.component';

describe('CauselistComponent', () => {
  let component: CauselistComponent;
  let fixture: ComponentFixture<CauselistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CauselistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CauselistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
