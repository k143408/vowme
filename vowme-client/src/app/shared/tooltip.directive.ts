import { Directive, ElementRef, Renderer, OnInit, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[tooltip]'
})
export class ToolTipDirective implements OnInit{
  
  constructor (private _elRef: ElementRef, private _renderer: Renderer) {}

  @Input('tooltip') tooltip:string;

  ngOnInit() {
    this._elRef.nativeElement.style.cursor= 'no-drop';
  }

}