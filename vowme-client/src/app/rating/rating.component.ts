import { Component, OnInit, Input, Output, HostListener, Self, EventEmitter } from '@angular/core';
import { ControlValueAccessor, NgModel } from "@angular/forms";


@Component({
  selector: 'rating[ngModel]',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class Rating implements ControlValueAccessor, OnInit {
  @Input() private max: number;
  @Input() private stateOn: string;
  @Input() private stateOff: string;
  @Input() private stateHalfOn: string;
  @Input() private readonly: boolean;
  @Input() private titles: Array<string>;
  @Input() private ratingStates: Array<{ stateOn: string, stateOff: string }>;

  @Output() private onHover: EventEmitter<number> = new EventEmitter();
  @Output() private onLeave: EventEmitter<number> = new EventEmitter();

  private range: Array<any>;
  private value: number;
  private preValue: number;

  @HostListener('keydown', ['$event'])
  private onKeydown(event: KeyboardEvent) {
    if ([37, 38, 39, 40].indexOf(event.which) === -1) {
      return;
    }

    event.preventDefault();
    event.stopPropagation();
    let sign = event.which === 38 || event.which === 39 ? 1 : -1;
    this.rate(this.value + sign);
  }

  constructor( @Self() public cd: NgModel) {
    cd.valueAccessor = this;
  }

  ngOnInit() {
    this.max = typeof this.max !== 'undefined' ? this.max : 5;
    this.readonly = this.readonly === true;
    this.stateOn = typeof this.stateOn !== 'undefined' ? this.stateOn : 'glyphicon-star';
    this.stateHalfOn = typeof this.stateHalfOn !== 'undefined' ? this.stateOn : 'glyphicon-star half';
    this.stateOff = typeof this.stateOff !== 'undefined' ? this.stateOff : 'glyphicon-star-empty';
    this.titles = typeof this.titles !== 'undefined' && this.titles.length > 0 ? this.titles : ['one', 'two', 'three', 'four', 'five'];
    this.range = this.buildTemplateObjects(this.ratingStates, this.max);
  }

  writeValue(value: number) {
    if (value % 1 !== value) {
      this.value = value;
      this.preValue = value;
      return;
    }

    this.preValue = value;
    this.value = value;
  }

  private buildTemplateObjects(ratingStates: Array<any>, max: number) {
    ratingStates = ratingStates || [];
    let count = ratingStates.length || max;
    let result: any[] = [];
    for (let i = 0; i < count; i++) {
      result.push(Object.assign({
        index: (i + 1).toFixed(1),
        stateOn: this.stateOn,
        stateOff: this.stateOff,
        stateHalfOn: this.stateHalfOn
      }, ratingStates[i] || {}));
    }
    return result;
  }

  private rate(value: number) {
    if (!this.readonly && value >= 0 && value <= this.range.length) {
      this.writeValue(value);
      this.cd.viewToModelUpdate(value);
    }
  }

  private enter(value: number) {
    if (!this.readonly) {
      this.value = value;
      this.onHover.emit(value);
    }else{
      this.onHover.emit(this.value);
    }
  }

  private reset() {
    this.value = this.preValue;
    this.onLeave.emit(this.value);
  }

  onChange = (_: any) => {
  };
  onTouched = () => {
  };

  registerOnChange(fn: (_: any) => {}): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => {}): void {
    this.onTouched = fn;
  }
}
